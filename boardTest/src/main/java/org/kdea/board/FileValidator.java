/*package org.kdea.board;
 
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;  
import org.springframework.validation.Validator;  
 
@Service("fileValidator")
public class FileValidator implements Validator {  
 
    public boolean supports(Class<?> arg0) {
        return false;
    }
 
    public void validate(Object uploadedFile, Errors errors) {  
           
        UploadedFile file = (UploadedFile) uploadedFile;  
           
        if (file.getFile().getSize() == 0) {  
            errors.rejectValue("file", "uploadForm.selectFile",  
                    "Please select a file!");  
        }
    }
}*/