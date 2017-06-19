package com.github.nlatyshev.sbertech

import org.springframework.core.io.AbstractResource
import org.springframework.core.io.WritableResource

class ByteArrayResource extends AbstractResource implements WritableResource {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    ByteArrayInputStream byteArrayInputStream;

    ByteArrayResource(String input) {
        byteArrayInputStream = new ByteArrayInputStream(input.bytes)
    }

    @Override
    boolean isWritable() {
        true
    }

    @Override
    OutputStream getOutputStream() throws IOException {
        byteArrayOutputStream
    }

    @Override
    String getDescription() {
        ""
    }

    @Override
    InputStream getInputStream() throws IOException {
        byteArrayInputStream
    }
}
