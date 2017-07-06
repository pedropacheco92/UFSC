package akka.ufsc;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.Terminated;

public class Main {

	public static void main(String[] args) {
		ActorSystem system = ActorSystem.create("Hello");
		ActorRef a = system.actorOf(Props.create(HelloWorld.class), "helloWorld");
		system.actorOf(Props.create(Terminator.class, a), "terminator");
	}

	public static class Terminator extends AbstractLoggingActor {

		private final ActorRef ref;

		public Terminator(ActorRef ref) {
			this.ref = ref;
			this.getContext().watch(ref);
		}

		@Override
		public Receive createReceive() {
			return this.receiveBuilder().match(Terminated.class, t -> {
				this.log().info("{} terminou, encerrando.", this.ref.path());
				this.getContext().system().terminate();
			}).build();
		}
	}
}