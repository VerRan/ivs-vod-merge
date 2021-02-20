package service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static com.amazonaws.auth.profile.internal.ProfileKeyConstants.REGION;

public class S3Service {

    private AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(REGION).build();


    public boolean checkFile(String srcBucket,String srcKey) {
        try {
            S3Object s3Object = s3Client.getObject(new GetObjectRequest(srcBucket, srcKey));
            InputStream objectData = s3Object.getObjectContent();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(objectData));
            String str = null;
            String currentFileTotalSecs = "";
            StringBuffer currentStrNoLastLine = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                String[] records = str.split(":");
                if (records.length > 0) {
                    if (records[0].equals("#EXT-X-TWITCH-TOTAL-SECS")) {
                        currentFileTotalSecs = records[1];
                        if(currentFileTotalSecs.equals("0.000")){
                            return true;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
