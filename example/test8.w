{ test arrays }
a:= array(10);
i:=0;
while i < 10
do(
    a[i] := i;
    write(a[i]);
    writeln;
    i:=i+1
)

