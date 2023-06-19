package db;

import java.io.Serial;

public class DbIntegrityException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = 1L;

    DbIntegrityException(String msg)
    {
        super(msg);
    }
}
