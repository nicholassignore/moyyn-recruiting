package com.moyyn.recruiting.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import jakarta.persistence.Entity;
import lombok.*;

@Builder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "resume")
public class Resume {

    @Id
    @GeneratedValue
    private String id;
    private String fileName;
    private String fileType;
    @Lob
    private byte[] data;

    public Resume(String fileName, String fileType, byte[] data) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
    }
}

