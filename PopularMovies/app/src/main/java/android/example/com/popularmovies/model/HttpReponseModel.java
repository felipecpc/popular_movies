package android.example.com.popularmovies.model;

/**
 * Created by felipe on 17/04/17.
 */



public class HttpReponseModel {

    private String responseData;
    private RequestStatus status;

    public HttpReponseModel(){

    }

    public HttpReponseModel(RequestStatus status,String responseData){
        this.responseData = responseData;
        this.status = status;
    }

    public String getResponseData() {
        return responseData;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public enum RequestStatus {
        SUCCESS  (0),
        FAIL(1)
        ;


        private final int requestStatus;

        private RequestStatus(int requestStatus) {
            this.requestStatus = requestStatus;
        }
    }





}
