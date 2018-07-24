package com.chengfy.blog.controller;

import com.chengfy.blog.domain.User;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oss")
@CrossOrigin(origins = "*")
public class OssController extends BaseController {

    @Value("${oss.access.key.id}")
    private String accessKeyId;

    @Value("${oss.access.key.secret}")
    private String accessKeySecret;

    @Value("${oss.bucket}")
    private String bucket;

    @Value("${oss.region}")
    private String region;


    @Secured({ROLE_WRITE_ARTICLES, ROLE_ADMINISTER})
    @GetMapping("/sig")
    public ResponseEntity<?> getSignature(){
        Signature signature = new Signature(accessKeyId, accessKeySecret, bucket, region);
        return restGetOk(signature, "Got signature successfully");
    }

    @Data
    private class Signature{
        private String accessKeyId;
        private String accessKeySecret;
        private String bucket;
        private String region;

        public Signature(String accessKeyId, String accessKeySecret,
                         String bucket, String region) {
            this.accessKeyId = accessKeyId;
            this.accessKeySecret = accessKeySecret;
            this.bucket = bucket;
            this.region = region;
        }
    }
}
