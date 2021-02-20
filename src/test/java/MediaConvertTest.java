import com.amazonaws.services.mediaconvert.model.CreateJobResult;
import org.junit.Test;
import service.AsyncMediaConvertService;
import service.MediaConvertService;
import service.S3Service;

//import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class MediaConvertTest {
    private  MediaConvertService mediaConvertService = new MediaConvertService();
    private  S3Service s3Service = new S3Service();

    private AsyncMediaConvertService  asyncMediaConvertService = new AsyncMediaConvertService();

    @Test
    public void testCheckFile(){

//        String inputFile = "s3://vod-liuhengtao/ivs/517141035927/RBb8M9xU3DBu/2021-01-26T11-58-28.33Z/CxbvSCiFgcR4/media/hls/master.m3u8";
//        String m3u8file_160p = inputFile.replace("master.m3u8","160p30/playlist.m3u8");
        String bucket = "vod-liuhengtao";
        String key = "ivs/517141035927/RBb8M9xU3DBu/2021-01-26T11-58-28.33Z/CxbvSCiFgcR4/media/hls/160p30/playlist.m3u8";
        if(!s3Service.checkFile(bucket,key)){
            System.out.println("input file is not valid");
        }
    }


    @Test
    public void testCreateJob(){
        String input1 = "s3://vod-liuhengtao/ivs/517141035927/RBb8M9xU3DBu/2021-01-26T11-58-28.33Z/CxbvSCiFgcR4/media/hls/master.m3u8";
        String input2 = "s3://vod-liuhengtao/ivs/517141035927/RBb8M9xU3DBu/2021-01-26T11-58-28.33Z/CxbvSCiFgcR4/media/hls/master.m3u8";
        String templateName = "ivs-vod-merge";
        String roleArn = "arn:aws:iam::517141035927:role/test-vod2-MediaConvertRole";
        String jobId = mediaConvertService.createJob(templateName,roleArn,input1,input2);
        String jobStatus = mediaConvertService.queryJobStatus(jobId);
        System.out.println(jobStatus);
    }


    @Test
    public void testAsyncCreateJob(){
        String input1 = "s3://vod-liuhengtao/ivs/517141035927/RBb8M9xU3DBu/2021-01-26T11-58-28.33Z/CxbvSCiFgcR4/media/hls/master.m3u8";
        String input2 = "s3://vod-liuhengtao/ivs/517141035927/RBb8M9xU3DBu/2021-01-26T11-58-28.33Z/CxbvSCiFgcR4/media/hls/master.m3u8";
        String templateName = "ivs-vod-merge";
        String roleArn = "arn:aws:iam::517141035927:role/test-vod2-MediaConvertRole";
        Future<CreateJobResult> jobResultFuture = asyncMediaConvertService.createJAsyncob(templateName,roleArn,input1,input2);
        try {
            while(!jobResultFuture.isDone()) {
//                System.out.println("job id is "+jobResultFuture.get().getJob().getId());
//                System.out.println("job status is "+jobResultFuture.get().getJob().getStatus());
                Thread.sleep(300);
            }
            System.out.println("kkk "+jobResultFuture.isDone());
            System.out.println();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        catch (ExecutionException e) {
//            e.printStackTrace();
//        }

    }



}
