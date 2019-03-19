package hr.ja.bio.form;

import hr.ja.bio.parser.model.SampleFileType;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class FileUploadForm {

    @NotNull
    Long projectId;

    SampleFileType fileType;

    List<MultipartFile> files;

}
