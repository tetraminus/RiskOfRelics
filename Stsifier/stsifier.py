import PIL
from PIL import Image
from PIL import ImageFilter
import os

def stsify(image_path):
    image = Image.open(image_path)
    #crop the image to the smallest square that can be made from the bbox
    image = image.crop(image.getbbox())
    width, height = image.size
    print(width, height)
    if width > height:
        new_image = Image.new('RGBA', (width, width), (0,0,0,0))
        new_image.paste(image, (0, (width - height) // 2))
    else:
        new_image = Image.new('RGBA', (height, height), (0,0,0,0))
        new_image.paste(image, ((height - width) // 2, 0))
    image = new_image  # Reassign image to the padded square image
    image = image.resize((256, 256), Image.BICUBIC)

    #normal is 128x128, with a image at 64x64 in the center
    #large is 256x256, with a image at 128x128 in the center
    #outline is also 128x128, with a 64x64 image in the center, thresholded to transparent or pure white, and expanded by a few pixels




    normalimage = Image.new('RGBA', (128, 128), (0,0,0,0))
    scaledimage = image.resize((64, 64), Image.BICUBIC)
    normalimage.paste(scaledimage, (32, 32))






    largeimage = Image.new('RGBA', (256, 256), (0,0,0,0))
    scaledimage = image.resize((128, 128), Image.BICUBIC)
    largeimage.paste(scaledimage, (64, 64))



    outlineimage = normalimage.copy()
    outlineimage = outlineimage.convert('RGBA')
    #get the alpha channel
    alpha = outlineimage.getchannel('A')
    #threshold the alpha channel
    alpha = alpha.point(lambda p: 0 if p < 200 else 255)
    #put the alpha channel back into a white image
    outlineimage = Image.new('RGBA', (128, 128), (255, 255, 255, 0))
    outlineimage.putalpha(alpha)
    #expand the image by a few pixels
    outlineimage = outlineimage.filter(ImageFilter.MaxFilter(3))
    outlineimage = outlineimage.filter(ImageFilter.MaxFilter(3))
    outlineimage = outlineimage.filter(ImageFilter.MaxFilter(3))
    #smooth the image
    outlineimage = outlineimage.filter(ImageFilter.SMOOTH_MORE)
    
   
   




    return normalimage, largeimage, outlineimage

if __name__ == '__main__':
    #create the output folders
    os.makedirs('output/normal', exist_ok=True)
    os.makedirs('output/large', exist_ok=True)
    os.makedirs('output/outline', exist_ok=True)


    #process in folder
    for file in os.listdir('in'):
        normal, large, outline = stsify(f'in/{file}')
        normal.save(f'output/normal/{file}')
        large.save(f'output/large/{file}')
        outline.save(f'output/outline/{file}')



    


    

