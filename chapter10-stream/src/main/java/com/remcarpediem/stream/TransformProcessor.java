package com.remcarpediem.stream;

import com.remcarpediem.stream.beans.Vote;
import com.remcarpediem.stream.beans.VoteResult;
import com.remcarpediem.stream.service.VotingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.handler.annotation.SendTo;


@EnableBinding(Processor.class)
public class TransformProcessor {
    @Autowired
    VotingService votingService;
    @StreamListener(Processor.INPUT)
    @SendTo(Processor.OUTPUT)
    public VoteResult handle(Vote vote) {
        return votingService.record(vote);
    }

}
