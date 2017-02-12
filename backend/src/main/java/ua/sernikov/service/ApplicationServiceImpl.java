package ua.sernikov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ua.sernikov.domain.Application;
import ua.sernikov.domain.Application.ApplicationBuilder;
import ua.sernikov.domain.NewApplicationRequest;
import ua.sernikov.domain.User;
import ua.sernikov.repository.ApplicationRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private ApplicationRepository applicationRepository;
    private PublisherService publisherService;

    @Autowired
    public ApplicationServiceImpl(ApplicationRepository applicationRepository, PublisherService publisherService) {
        this.applicationRepository = applicationRepository;
        this.publisherService = publisherService;
    }

    @Override
    public List<Application> getAllApplications() {
        return applicationRepository.findAll().stream()
                                    .collect(Collectors.toList());
    }

    @Override
    public List<Application> getAllPublisherApplications(String publisherKey) {
        Assert.hasText(publisherKey);
        return applicationRepository.findAllByOwnerKey(publisherKey).stream()
                                    .collect(Collectors.toList());
    }

    @Override
    public Application getApplicationByKey(String applicationKey) {
        Assert.hasText(applicationKey);
        return applicationRepository.findByKey(applicationKey).orElse(null);
    }

    @Override
    public Application createApplication(NewApplicationRequest applicationRequest) {
        Assert.notNull(applicationRequest);

        User publisher = publisherService.getPublisherByKey(applicationRequest.getPublisherKey());

        Application newApplication = ApplicationBuilder.anApplication()
                                                       .withKey(UUID.randomUUID().toString())
                                                       .withName(applicationRequest.getName())
                                                       .withType(applicationRequest.getType())
                                                       .withPublisher(publisher)
                                                       .withContentTypes(applicationRequest.getContentTypes())
                                                       .build();

        return applicationRepository.save(newApplication);
    }

    @Override
    @Transactional
    public Application updateApplication(Application application) {
        throw new UnsupportedOperationException("Update not implemented yet");
    }

    @Override
    @Transactional
    public Application deleteApplication(String applicationKey) {
        throw new UnsupportedOperationException("Delete not implemented yet");
    }
}
