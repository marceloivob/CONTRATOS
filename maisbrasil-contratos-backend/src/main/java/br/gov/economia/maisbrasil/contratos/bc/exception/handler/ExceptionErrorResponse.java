package br.gov.economia.maisbrasil.contratos.bc.exception.handler;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ExceptionErrorResponse extends RuntimeException {

	private static final long serialVersionUID = 1L;

    private Date timestamp;
    private String message;
    private String details;
    private int status;
    private List<MensagemErroDTO> messagesList;

    public ExceptionErrorResponse(Date timestamp, String message, String details, int status) {
        super();
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
        this.status = status;
    }
    
    public ExceptionErrorResponse(Date timestamp, String message, String details, List<MensagemErroDTO> messagesList, int status) {
        super();
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
        this.messagesList = messagesList;
        this.status = status;
    }
       
    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

	public List<MensagemErroDTO> getMessagesList() {
		return messagesList;
	}

	public void setMessagesList(List<MensagemErroDTO> messagesList) {
		this.messagesList = messagesList;
	}
	
    @JsonIgnore
	@Override
	public StackTraceElement[] getStackTrace() {
		// TODO Auto-generated method stub
		return super.getStackTrace();
	}

    
}
