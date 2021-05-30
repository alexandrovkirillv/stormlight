package com.secretNet.secNet.repo;

import com.secretNet.secNet.models.Pulse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PulseRepo extends JpaRepository<Pulse, Long> {
}
