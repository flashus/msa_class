package ru.idyachenko.users.repository;

import org.springframework.data.repository.CrudRepository;

import ru.idyachenko.users.entity.Subscription;
import java.util.UUID;

public interface SubscriptionRepository extends CrudRepository<Subscription, UUID> {
}
