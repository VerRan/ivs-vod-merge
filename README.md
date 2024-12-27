# IVS VOD Merge Utility

A Java utility for merging Amazon IVS (Interactive Video Service) VOD files using AWS MediaConvert. This tool enables merging multiple m3u8 files into a single video output through AWS MediaConvert service.

## Features

- Merge multiple m3u8 files from IVS recordings
- Asynchronous processing with job status tracking
- S3 file validation to prevent MediaConvert processing errors
- Template-based MediaConvert job configuration
- AWS SDK integration for MediaConvert and S3 services

## Prerequisites

- Java 7 or higher
- Maven
- AWS Account with access to:
  - AWS MediaConvert
  - Amazon S3
  - AWS IAM (for role configuration)

## Setup

1. Import the MediaConvert job template:
   - Navigate to AWS MediaConvert console
   - Import the template from `template/job.json`

2. Configure IAM Role:
   - Create an IAM role for MediaConvert
   - Ensure the role has necessary permissions for MediaConvert and S3
   - For detailed setup instructions, refer to [AWS Media Services Simple VOD Workflow](https://github.com/aws-samples/aws-media-services-simple-vod-workflow/tree/master/1-IAMandS3)

3. Build the project:
   ```bash
   mvn clean install
   ```

## Usage

### Integration with Backend API

1. The merging process is typically triggered through a mobile app calling your backend API
2. Your API implementation should:
   - Call the MediaConvert service using this utility
   - Store the job ID for status tracking
   - Update the status through callback functions
   - Display the merged video in VOD playlist

### Code Example

```java
// Initialize the service
MediaConvertService service = new MediaConvertService();

// Create a merge job
String jobId = service.createJob(
    "templateName",
    "roleArn",
    "s3://bucket/file1.m3u8",
    "s3://bucket/file2.m3u8"
);

// Check job status
String status = service.queryJobStatus(jobId);
```

## Testing

Run the included test suite using:
```bash
mvn test
```

The `MediaConvertTest` class provides examples of:
- Asynchronous job execution
- Callback function implementation
- M3u8 file validation

## Changelog

### 2021-01-21
- Added validation for m3u8 files with 0 duration
- Fixed MediaConvert conversion issues
- Implementation details in `S3Service` class
- Test cases available in `MediaConvertTest.checkFile()`

## Dependencies

- AWS Java SDK MediaConvert (1.11.747)
- AWS Java SDK Core (1.11.747)
- AWS Java SDK S3 (1.11.747)
- JUnit (4.12) for testing

## License

This project is licensed under the MIT License - see the LICENSE file for details.
