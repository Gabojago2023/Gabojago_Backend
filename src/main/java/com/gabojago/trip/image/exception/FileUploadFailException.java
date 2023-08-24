package com.gabojago.trip.image.exception;

import com.gabojago.trip.common.exception.BadRequestException;

public class FileUploadFailException extends BadRequestException {

    public FileUploadFailException(String reason) {
        super(reason);
    }
}
