package org.example.exception;

public class CannotUploadCollectionException extends RuntimeException {
  public CannotUploadCollectionException() {
    super();
  }
  public CannotUploadCollectionException(String message) {
    super(message);
  }
}
