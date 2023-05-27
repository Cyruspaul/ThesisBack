package com.cyrus.applicant.feign;

import com.cyrus.applicant.models.ApplicationInfo;
import com.cyrus.common.config.APIResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@CircuitBreaker(name = "fooClient")
@Retry(name = "retryApi")
@FeignClient(name = "schoolModule", url = "http://localhost:8085/api/school", fallback = ApplicationCallBack.class) //
public interface SchoolModuleFeignClient {

    @PostMapping("/applicant/getOne/{id}")
    APIResponse<?> getOneApplication(@PathVariable("id") String id);

    @PostMapping("/applicant/saveChange/{id}")
    APIResponse<?> saveChange(ApplicationInfo applicationInfo);

    @PostMapping("/applicant/registerNew/{id}")
    APIResponse<?> registerNew(ApplicationInfo applicantRegisterDTO);
}
