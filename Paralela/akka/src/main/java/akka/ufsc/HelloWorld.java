package akka.ufsc;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.ufsc.Greeter.Msg;

public class HelloWorld extends AbstractActor {

	@Override
	public Receive createReceive() {
		return this.receiveBuilder().matchEquals(Msg.DONE, m -> {
			this.getContext().stop(this.self());
		}).build();
	}

	@Override
	public void preStart() {
		final ActorRef greeter = this.getContext().actorOf(Props.create(Greeter.class), "greeter");
		greeter.tell(Msg.GREET, this.self());
	}
}