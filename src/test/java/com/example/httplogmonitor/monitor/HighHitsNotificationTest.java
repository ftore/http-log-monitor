package com.example.httplogmonitor.monitor;

import com.example.httplogmonitor.repository.AccessLogEntryRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class HighHitsNotificationTest {
    @Rule
    public OutputCapture outputCapture = new OutputCapture();

    @MockBean
    private AccessLogEntryRepository repository;

    @Autowired
    private LogAggregatorTask aggregatorTask;

    @Test
    public void aggregateHitsWhenHitsAreHighShouldShowAlert() throws Exception {
        given(repository.findTotalHits()).willReturn(() -> 10000);
        //LogAggregatorTask aggregatorTask = new LogAggregatorTask();
        aggregatorTask.aggregateHits();

        assertThat(outputCapture.toString(), containsString("High traffic generated an alert"));
        verify(repository, times(1)).findTotalHits();
    }

    @Test
    public void aggregateHitsWhenHitsAreLowShouldShowRecoveredInfo() throws Exception {
        given(repository.findTotalHits()).willReturn(() -> 1000);
        //LogAggregatorTask aggregatorTask = new LogAggregatorTask();
        aggregatorTask.aggregateHits();

        assertThat(outputCapture.toString(), containsString("High traffic alert recovered"));
        verify(repository, times(1)).findTotalHits();
    }
}
