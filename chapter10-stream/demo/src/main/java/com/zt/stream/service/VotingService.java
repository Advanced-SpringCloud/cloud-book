package com.zt.stream.service;

import com.zt.stream.beans.Vote;
import com.zt.stream.beans.VoteResult;
import org.springframework.stereotype.Service;

@Service
public class VotingService {
    public VoteResult record(Vote vote) {
        return new VoteResult();
    }
}
