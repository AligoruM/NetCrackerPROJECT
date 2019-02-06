package catalogApp.server.service;

import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class ImageService implements IImageService {

    private String workDir;

    private String uploadDir;

    public void setWorkDir(String workDir) {
        this.workDir = workDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }

    @Override
    public void saveImage(InputStream stream, String filename) {
        try
        {
            int read;
            byte[] bytes = new byte[1024];
            OutputStream out = new FileOutputStream(new File(workDir + uploadDir + filename));
            while ((read = stream.read(bytes)) != -1)
            {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
