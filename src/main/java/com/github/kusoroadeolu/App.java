import com.github.kusoroadeolu.SnapshotAdvisor;
import com.github.kusoroadeolu.Foo;
import com.github.kusoroadeolu.annotations.SnapSource;
import com.github.kusoroadeolu.annotations.Mutates;
import com.github.kusoroadeolu.structures.Graph;
import com.github.kusoroadeolu.structures.Node;
import net.bytebuddy.agent.builder.AgentBuilder;

import java.lang.instrument.Instrumentation;

import static net.bytebuddy.asm.Advice.to;
import static net.bytebuddy.matcher.ElementMatchers.isAnnotatedWith;

void main() throws IOException, IllegalAccessException {
    //premain(null, ByteBuddyAgent.install());
    var returnType = (new Foo()).test("Vic", 1, LocalDateTime.now());
    var returnType2 = (new Foo()).test("Vic2", 1, LocalDateTime.now());
    Foo f = new Foo();
    Node n = new Node(f, f.getClass(), f.getClass().getSimpleName(), new ArrayList<>());
    Graph g = new Graph(n);
    g.buildGraph(n, 3);
    n.children().forEach(e -> IO.println("E: " + e.fieldName()));
}







public static void premain(String args, Instrumentation instrumentation) {
    AgentBuilder.Default agent = new AgentBuilder.Default();
    agent.type(isAnnotatedWith(SnapSource.class))
            .transform(((b, _, _, _, _) -> b.method(isAnnotatedWith(Mutates.class)).intercept(to(SnapshotAdvisor.class))))
            .installOn(instrumentation);
}