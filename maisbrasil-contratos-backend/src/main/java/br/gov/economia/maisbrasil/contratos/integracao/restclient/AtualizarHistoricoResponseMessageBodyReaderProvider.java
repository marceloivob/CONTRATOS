package br.gov.economia.maisbrasil.contratos.integracao.restclient;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AtualizarHistoricoResponseMessageBodyReaderProvider
		implements MessageBodyReader<AtualizarHistoricoResponse> {

	private ObjectMapper mapper = new ObjectMapper();

	@Override
	public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return type == AtualizarHistoricoResponse.class;
	}

	@Override
	public AtualizarHistoricoResponse readFrom(Class<AtualizarHistoricoResponse> type, Type genericType,
			Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders,
			InputStream entityStream) throws IOException {

		AtualizarHistoricoResponse response = mapper.readValue(entityStream, AtualizarHistoricoResponse.class);

		return response;
	}

}
