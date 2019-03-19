package hr.ja.bio.parser.old;

import lombok.Data;

import java.io.Serializable;

@Deprecated
@Data
public class TaskResult implements Serializable {
	String userMessage;
}
