package com.esprit.jobfinder.services;

import com.esprit.jobfinder.models.User;
import com.esprit.jobfinder.models.enums.ERole;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {

    public static Specification<User> hasName(String name) {
        return (Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            if (name == null) {
                return null;
            }
            String namePattern = "%" + name + "%";
            Predicate firstNamePredicate = criteriaBuilder.like(root.get("firstName"), namePattern);
            Predicate lastNamePredicate = criteriaBuilder.like(root.get("lastName"), namePattern);
            return criteriaBuilder.or(firstNamePredicate, lastNamePredicate);
        };
    }

    public static Specification<User> hasEmail(String email) {
        return (root, query, criteriaBuilder) ->
                email == null ? null : criteriaBuilder.like(root.get("email"), "%" + email + "%");
    }

    public static Specification<User> hasRole(String role) {
        return (root, query, criteriaBuilder) ->
                role == null ? null : criteriaBuilder.equal(root.get("role"), role);
    }

    public static Specification<User> hasPhone(String phone) {
        return (root, query, criteriaBuilder) ->
                phone == null ? null : criteriaBuilder.like(root.get("phone"), "%" + phone + "%");
    }
}
