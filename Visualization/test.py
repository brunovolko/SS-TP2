import imageio
images = list()
for i in range(13):
    images.append(imageio.imread("figures/"+str(i)+".png"))
imageio.mimsave('animation.gif', images, duration=0.15)