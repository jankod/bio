package hr.ja.bio.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class FileUploadForm {

    private List<MultipartFile> files;

}
