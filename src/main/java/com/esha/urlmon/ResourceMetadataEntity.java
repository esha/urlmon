package com.esha.urlmon;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "resource_metadata")
@Data
@RequiredArgsConstructor
public class ResourceMetadataEntity {

    @Id
    @Column(updatable = false)
    private final Integer id;

    private final String url, version;

    @Column(name = "last_modified")
    private final Long lastModified;

    ResourceMetadataEntity() {
        this.id = null;
        this.url = null;
        this.version = null;
        this.lastModified = null;
    }
}
