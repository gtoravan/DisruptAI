from openai import OpenAI

if __name__ == '__main__':
    client = OpenAI(api_key="API KEY")

    img = client.images.edit(
        image=open("pngcoke.png", "rb"),
        mask=open("cokecan.png", "rb"),
        prompt="Create an image of a vibrant, energetic nightclub scene with a group of young, stylish people dancing and having fun. In the center of the image, place a glowing, ice-cold Coke can, condensation dripping down its sides, with the logo prominently displayed. Surround the can with dynamic light effects and party decorations to enhance the festive atmosphere. The scene should be so captivating that anyone scrolling through Instagram would instantly crave a refreshing Coke.",
        n=2,
        size="512x512"
    )

    print(img.data[0].url)


# refer to dalle1.png for results
# refer to dalle2.png for results