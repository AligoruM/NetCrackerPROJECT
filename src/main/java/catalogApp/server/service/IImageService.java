package catalogApp.server.service;

import java.io.InputStream;

public interface IImageService {
    void saveImage(InputStream bytes, String filename);
}
