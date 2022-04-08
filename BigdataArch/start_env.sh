for i in master slave1 slave2
do
  multipass start $i &
done
