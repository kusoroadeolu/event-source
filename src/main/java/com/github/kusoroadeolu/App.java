import com.github.kusoroadeolu.EventAdvisor;
import com.github.kusoroadeolu.EventStoreHolder;
import com.github.kusoroadeolu.Foo;
import com.github.kusoroadeolu.annotations.EventSource;
import com.github.kusoroadeolu.annotations.Mutates;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.agent.builder.AgentBuilder;

import java.lang.instrument.Instrumentation;

import static net.bytebuddy.asm.Advice.to;
import static net.bytebuddy.matcher.ElementMatchers.isAnnotatedWith;

void main() throws IOException {
    premain(null, ByteBuddyAgent.install());
    var returnType = (new Foo()).test("Vic", 1, LocalDateTime.now());
    EventStoreHolder.getEvents().forEach((k, v) -> IO.println("Key: %s, Val: %s".formatted(k, v)));
}




public static void premain(String args, Instrumentation instrumentation) {
    AgentBuilder.Default agent = new AgentBuilder.Default();
    agent.type(isAnnotatedWith(EventSource.class))
            .transform(((b, _, _, _, _) -> b.method(isAnnotatedWith(Mutates.class)).intercept(to(EventAdvisor.class))))
            .installOn(instrumentation);
}