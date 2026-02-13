package com.sudheer.aisolver.repository;

import com.sudheer.aisolver.entity.ProblemHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemHistoryRepository
        extends JpaRepository<ProblemHistory, Long> {
}
