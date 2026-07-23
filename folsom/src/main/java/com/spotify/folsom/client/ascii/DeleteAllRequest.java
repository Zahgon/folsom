package com.spotify.folsom.client.ascii;

import com.spotify.folsom.MemcacheStatus;
import com.spotify.folsom.client.AllRequest;
import com.spotify.folsom.client.Request;
import java.util.List;

public class DeleteAllRequest extends DeleteRequest implements AllRequest<MemcacheStatus> {

    public DeleteAllRequest(byte[] key) {
        super(key);
    }

    @Override
    public MemcacheStatus merge(List<MemcacheStatus> results) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Request<MemcacheStatus> duplicate() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
