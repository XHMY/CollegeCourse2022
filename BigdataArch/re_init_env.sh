for i in master slave1 slave2
do
  multipass delete $i
done

multipass purge

for i in master slave1 slave2
do
  multipass launch -n $i --cloud-init cloud-config.yaml &
done
