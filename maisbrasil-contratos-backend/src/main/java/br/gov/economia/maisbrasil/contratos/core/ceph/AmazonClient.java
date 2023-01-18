package br.gov.economia.maisbrasil.contratos.core.ceph;

import static com.amazonaws.services.s3.internal.Constants.MB;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.amazonaws.SDKGlobalConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;

import br.gov.economia.maisbrasil.contratos.core.exceptions.MandatariasException;
import io.github.jhipster.config.JHipsterConstants;
import lombok.Data;

@Service
@Data
public class AmazonClient {

	private Logger logger = LoggerFactory.getLogger(AmazonClient.class);

	private AmazonS3 s3client;

	@Value("${integrations.PRIVATE.CEPH.endpoint}")
	private String endpointUrl;

	@Value("${integrations.PRIVATE.CEPH.useSSL}")
	private Boolean ssl;

	@Value("${integrations.PRIVATE.CEPH.region}")
	private String region;

	@Value("${integrations.PRIVATE.CEPH.bucket}")
	private String bucketName;

	@Value("${integrations.PRIVATE.CEPH.secretKey}")
	private String secretKey;

	@Value("${integrations.PRIVATE.CEPH.accessToken}")
	private String accessToken;

	@Value("${integrations.PRIVATE.CEPH.maxFileSizeInMB}")
	private String maxFileSizeInMB;

	@Autowired
	private final Environment env;

	@PostConstruct
	private void initializeAmazon() {
		ignoraCertificado();

		AWSCredentials credentials = new BasicAWSCredentials(this.accessToken, this.secretKey);
		AWSCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);

		EndpointConfiguration epc = new AwsClientBuilder.EndpointConfiguration(this.endpointUrl, this.region);

		this.s3client = AmazonS3ClientBuilder.standard().withEndpointConfiguration(epc)
				.withCredentials(credentialsProvider).withPathStyleAccessEnabled(true).build();

		// Do not make bucket create or delete calls in the high availability code path
		// of an application. Create or delete buckets in a separate
		// initialization or setup routine that runs less often.
		// Código trazido para inicialização pela razão citada acima!!!
		s3client.createBucket(bucketName);
	}

	private void ignoraCertificado() {
		Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());

		if (activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT)) {
			logger.info("Desabilitando a validação do uso de Certificado quando usando o protocolo HTTPS");

			/**
			 * Disable validation of server certificates when using the HTTPS protocol. This
			 * should ONLY be used to do quick smoke tests against endpoints which don't yet
			 * have valid certificates; it should NEVER be used in production. This property
			 * is meant to be used as a flag (i.e. -Dcom.amazonaws.sdk.disableCertChecking)
			 * rather then taking a value (-Dcom.amazonaws.sdk.disableCertChecking=true).
			 * This property is treated as false by default (i.e. check certificates by
			 * default)
			 * <p>
			 * https://docs.aws.amazon.com/AWSJavaSDK/latest/javadoc/com/amazonaws/SDKGlobalConfiguration.html#DISABLE_CERT_CHECKING_SYSTEM_PROPERTY
			 */
			System.setProperty(SDKGlobalConfiguration.DISABLE_CERT_CHECKING_SYSTEM_PROPERTY, Boolean.TRUE.toString());
		}
	}

	public String deleteFileFromS3Bucket(String fileUrl) {
		String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
		getS3client().deleteObject(new DeleteObjectRequest(getBucketName() + "/", fileName));
		return "Successfully deleted";
	}

	public AmazonS3 getS3client() {
		return s3client;
	}

	// em bytes
	public Integer getMaxFileSize() {
		Integer fileSize = Integer.valueOf(maxFileSizeInMB);

		return fileSize * MB;
	}

	public void parse(Map<String, String> properties) {
		this.endpointUrl = properties.get("integrations.PRIVATE.CEPH.endpoint");

		if (properties.get("integrations.PRIVATE.CEPH.useSSL") != null) {
			this.ssl = Boolean.parseBoolean(properties.get("integrations.PRIVATE.CEPH.useSSL"));
		}

		this.region = properties.get("integrations.PRIVATE.CEPH.region");
		this.setBucketName(this.getBucketName(properties.get("integrations.PRIVATE.CEPH.bucket")));
		this.secretKey = properties.get("integrations.PRIVATE.CEPH.secretKey");
		this.accessToken = properties.get("integrations.PRIVATE.CEPH.accessToken");
		this.maxFileSizeInMB = properties.get("integrations.PRIVATE.CEPH.maxFileSizeInMB");

	}

	public String getBucketName(String seed) {

		int ano = LocalDate.now().getYear();
		int mes = LocalDate.now().getMonthValue();

		String bucket = String.format("%s-%02d-%d", seed, mes, ano);

		return bucket;

	}

	public String uploadFile(@NotNull final InputStream inputStream, @NotNull final String filename,
			@NotNull final Long fileLength) {

		try {
			String encodedFilename = URLEncoder.encode(filename, "UTF-8").replace("+", "%20");
			final String bucketName = getBucketName();

			String key = filename + "_" + UUID.randomUUID();
			key = URLEncoder.encode(key, "UTF-8").replace("+", "%20");

			ObjectMetadata objectMetadata = new ObjectMetadata();

			objectMetadata.setContentLength(fileLength);
			objectMetadata.addUserMetadata("filename", encodedFilename);
			objectMetadata.setContentDisposition("attachment; filename=" + encodedFilename);

			logger.info("Upload do arquivo '{}' com tamanho de '{}' bytes no bucket '{}' usando a key '{}'",
					encodedFilename, fileLength, bucketName, key);

			this.s3client.createBucket(bucketName);
			this.s3client.putObject(bucketName, key, inputStream, objectMetadata);
			this.s3client.setObjectAcl(bucketName, key, CannedAccessControlList.Private);

			return key;
		} catch (IOException ioe) {
			throw new MandatariasException(ioe);
		}
	}

}
