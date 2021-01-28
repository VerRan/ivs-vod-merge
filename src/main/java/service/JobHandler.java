package service;

import com.amazonaws.handlers.AsyncHandler;
import com.amazonaws.services.mediaconvert.model.CreateJobRequest;
import com.amazonaws.services.mediaconvert.model.CreateJobResult;

public class JobHandler implements AsyncHandler<CreateJobRequest, CreateJobResult> {

    private AsyncMediaConvertService asyncMediaConvertService = new AsyncMediaConvertService();
    @Override
    public void onError(Exception e) {
        System.out.println("Error");
    }

    @Override
    public void onSuccess(CreateJobRequest request, CreateJobResult createJobResult) {
        System.out.println("Success");
        System.out.println("job id is "+createJobResult.getJob().getId());
        while(true) {
            String jobStatus = asyncMediaConvertService.queryJobStatus(createJobResult.getJob().getId());
            if (jobStatus.equals("COMPLETE")) {
                System.out.println("update rds");
                break;
            } else {
                System.out.println("job status is  " + jobStatus);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }
        }

    }

}