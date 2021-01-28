package service;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.mediaconvert.AWSMediaConvert;
import com.amazonaws.services.mediaconvert.AWSMediaConvertAsync;
import com.amazonaws.services.mediaconvert.AWSMediaConvertAsyncClientBuilder;
import com.amazonaws.services.mediaconvert.AWSMediaConvertClientBuilder;
import com.amazonaws.services.mediaconvert.model.*;
import java.util.*;

public class MediaConvertService {

    private static AWSMediaConvert mediaConvert = AWSMediaConvertClientBuilder.standard()
            .withRegion(Regions.US_WEST_2)
            .build();

    /**
     * 初始化MediaConvert客户端
     * */
    static {
        DescribeEndpointsRequest describeEndpointsRequest =new DescribeEndpointsRequest();
        DescribeEndpointsResult endpoint =  mediaConvert.describeEndpoints(describeEndpointsRequest);
        String endpointUrl = endpoint.getEndpoints().get(0).getUrl();

        AwsClientBuilder.EndpointConfiguration endpointConfiguration
                =new AwsClientBuilder.EndpointConfiguration(endpointUrl,Regions.US_WEST_2.getName());

        mediaConvert = AWSMediaConvertClientBuilder.standard()
                .withEndpointConfiguration(endpointConfiguration)
                .build();
    }


    public void MediaConvertService(){
    }




    /**
     *
     * @param templateName
     * @param roleArn
     * @param  req1 用于合并的m3u8 文件
     * @param  req2 用于合并的m3u8 文件
     * @return 返回jobId
     *
     * */
    public String createJob(String templateName, String roleArn, String req1, String req2) {
        CreateJobRequest createJobRequest =new CreateJobRequest();
        createJobRequest.setRole(roleArn);
        Map<String, String> userMetadata = new HashMap<>();
        userMetadata.put("assetID", UUID.randomUUID()+"");
        createJobRequest.setUserMetadata(userMetadata);

        GetJobTemplateRequest getJobTemplateRequest = new GetJobTemplateRequest();
        getJobTemplateRequest.setName(templateName);
        GetJobTemplateResult getJobTemplateResult =  mediaConvert.getJobTemplate(getJobTemplateRequest);
        JobTemplate jobTemplate = getJobTemplateResult.getJobTemplate();

        createJobRequest.setJobTemplate(jobTemplate.getArn());

        JobSettings jobSettings = new JobSettings();
        List<Input> inputs = new ArrayList();
        Input input_1 =new Input();
        input_1.setFileInput(req1);
        inputs.add(input_1);

        Input input_2 =new Input();
        input_2.setFileInput(req2);
        inputs.add(input_2);

        jobSettings.setInputs(inputs);

        createJobRequest.setSettings(jobSettings);
        CreateJobResult createJobResult =  mediaConvert.createJob(createJobRequest);
        return createJobResult.getJob().getId();
    }


    /**
     *
     * @param jobId 任务ID
     * @return 返回任务的执行状态
     *
     * */
    public String queryJobStatus(String jobId) {
        GetJobRequest getJobRequest = new GetJobRequest();
        getJobRequest.setId(jobId);
        GetJobResult getJobResult = mediaConvert.getJob(getJobRequest);
        return getJobResult.getJob().getStatus();
    }

}
