package akka.ufsc;

import akka.actor.AbstractActor;

public class Greeter extends AbstractActor {

	public static enum Msg {
		GREET,
		DONE;
	}

	@Override
	public Receive createReceive() {
		return this.receiveBuilder().matchEquals(Msg.GREET, m -> {
			System.out.println("Hello World!");
			this.sender().tell(Msg.DONE, this.self());
		}).build();
	}

}
