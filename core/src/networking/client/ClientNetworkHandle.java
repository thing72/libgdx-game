package networking.client;

import infra.entity.EntityManager;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import networking.NetworkObject;
import networking.NetworkObjectServiceGrpc;

import java.util.Scanner;
import java.util.UUID;

public class ClientNetworkHandle {

    static ClientNetworkHandle instance;
    public static String host = "localhost";
    public static int port = 99;
    final public EntityManager entityManager;
    final private UUID id;

    public static ClientNetworkHandle getInstance() {
        if (instance == null) {
            instance = new ClientNetworkHandle(host, port);
        }
        return instance;
    }

    private final ManagedChannel channel;
    private final NetworkObjectServiceGrpc.NetworkObjectServiceBlockingStub blockingStub;
    private final NetworkObjectServiceGrpc.NetworkObjectServiceStub asyncStub;


    public StreamObserver<NetworkObject.CreateNetworkObject> createObserver;
    public StreamObserver<NetworkObject.UpdateNetworkObject> updateObserver;
    public StreamObserver<NetworkObject.RemoveNetworkObject> removeObserver;
    // responders
    public StreamObserver<NetworkObject.CreateNetworkObject> createRequest;
    public StreamObserver<NetworkObject.UpdateNetworkObject> updateRequest;
    public StreamObserver<NetworkObject.RemoveNetworkObject> removeRequest;

    public ClientNetworkHandle(String host, int port) {
        this.channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
        this.blockingStub = NetworkObjectServiceGrpc.newBlockingStub(channel);
        this.asyncStub = NetworkObjectServiceGrpc.newStub(channel);
        this.id = UUID.randomUUID();
        this.entityManager = EntityManager.getInstance(id);
    }

    public void connect() {
        // receivers
        createObserver = new CreateObserver(this.id);
        updateObserver = new UpdateObserver(this.id);
        removeObserver = new RemoveObserver(this.id);
        // responders
        createRequest = this.asyncStub.create(createObserver);
        updateRequest = this.asyncStub.update(updateObserver);
        removeRequest = this.asyncStub.remove(removeObserver);
    }

    public static void main(String args[]) throws InterruptedException {
        Scanner myInput = new Scanner(System.in);

        ClientNetworkHandle client = ClientNetworkHandle.getInstance();
        client.connect();
        System.out.println("starting..!");
        while (true) {
            String id = myInput.nextLine();
            NetworkObject.CreateNetworkObject createRequestObject = NetworkObject.CreateNetworkObject.newBuilder().setId(id).build();
            client.createRequest.onNext(createRequestObject);
        }
    }

}
