package com.github.kusoroadeolu.structures;

import com.github.kusoroadeolu.utils.Constants;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.github.kusoroadeolu.utils.Constants.DELIMITER;

public record Graph(
        Node root
) {

    public void buildGraph(Node root, int depth) throws IllegalAccessException {  //TODO Change impl to dfs
        final Object o = root.val();
        final Field[] fields = o.getClass().getDeclaredFields();
        if(depth == 0 || fields.length == 0)return;
        final List<Node> l = root.children();
        for (final Field f : fields){
            f.setAccessible(true);
            final Class<?> clazz = f.getType();
            final Object val = f.get(o);
            final Node n = new Node(val, clazz, root.fieldName() + DELIMITER + f.getName(), new ArrayList<>());
            l.add(n);
            if(val == null || isStdJdkType(clazz)) continue;
            buildGraph(n, depth - 1);
        }
    }


    private void getDiffs(Node before, Node after, List<FieldDiff> fieldDiffs){
        if(before == null && after == null) return;

        if(before == null){

        }

        List<Node> bc = before.children();
        List<Node> ac = after.children();
        if(ac.isEmpty() && bc.isEmpty()) return;
        for (int i = 0; i < bc.size(); i++){
            Node bn = bc.get(i);
            Node an = ac.get(i);
            if(bn.equals(an))return;
            else fieldDiffs.add(new FieldDiff(bn.fieldName(), bn.val(), an.val()));

            getDiffs(bn, an, fieldDiffs);
        }
    }

    private boolean isStdJdkType(Class<?> clazz) {
        String name = clazz.getName();
        return clazz.isPrimitive()
                || name.startsWith("java.")
                || name.startsWith("javax.")
                || name.startsWith("jdk.")
                || clazz.isEnum();
    }

}