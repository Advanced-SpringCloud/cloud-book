package com.remcarpediem.stream.service;

import com.remcarpediem.stream.beans.Vote;
import com.remcarpediem.stream.beans.VoteResult;
import org.springframework.stereotype.Service;

@Service
public class VotingService {
    public VoteResult record(Vote vote) {
        return new VoteResult();
    }
}
