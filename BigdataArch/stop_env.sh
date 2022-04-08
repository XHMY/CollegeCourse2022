for i in master slave1 slave2
do
  multipass stop $i &
done
