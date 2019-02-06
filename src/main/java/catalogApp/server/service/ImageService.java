package catalogApp.server.service;

import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class ImageService implements IImageService {

    private String uploadPath;

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    @Override
    public boolean saveImage(InputStream stream, String filename) {
        try
        {
            int read;
            byte[] bytes = new byte[1024];

            OutputStream out = new FileOutputStream(new File(uploadPath + filename));
            while ((read = stream.read(bytes)) != -1)
            {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
            return true;
        } catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }
}
