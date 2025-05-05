package org.example.base.response;

import java.util.Date;

/**
 * ServerResponseInfo - описание класса.
 *
 * @version 1.0
 */

public class ServerResponseInfo extends ServerResponse {
    private String collectionType;
    private Date creationDate;
    private int elementsCount;

    public ServerResponseInfo(ServerResponseType type, String message, String collectionType, Date creationDate, int elementsCount) {
        super(type, message);
        this.collectionType = collectionType;
        this.creationDate = creationDate;
        this.elementsCount = elementsCount;
    }

    public String getCollectionType() {
        return collectionType;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public int getElementsCount() {
        return elementsCount;
    }
}
