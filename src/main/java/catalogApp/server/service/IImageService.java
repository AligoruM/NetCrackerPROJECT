package catalogApp.server.service;

import java.io.InputStream;

public interface IImageService {
    boolean saveImage(InputStream bytes, String filename);
}
