package hr.ja.bio.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class FileUploadForm {

    @NotNull
    Long projectId;

    List<MultipartFile> files;

}
