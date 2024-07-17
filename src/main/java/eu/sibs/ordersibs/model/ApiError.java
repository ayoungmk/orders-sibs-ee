package eu.sibs.ordersibs.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.ws.rs.core.Response;


/**
 * Class that represents an error message. This error is used by the exceptions to
 * simplify its use.
 * <p>
 * Each error message, will have 3 fields:
 * <ul>
 * <li>An error message, that provides information about the error.</li>
 * <li>An error code, that provides the {@link Response.Status} error.</li>
 * <li>A documentation, that provides additional information.</li>
 * </ul>
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {

    private String errorMessage;
    private int errorCode;
    private String documentation;

}