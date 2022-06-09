package com.myhotels.guestservice.entities;

import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "stay_info")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StayInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "stay_id", nullable = false)
    private Integer stayId;
    private Integer hotelId;
    @ElementCollection
    private Set<Integer> roomNumbers;
}