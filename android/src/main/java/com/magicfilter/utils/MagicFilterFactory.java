package com.magicfilter.utils;

import com.magicfilter.advanced.MagicAmaroFilter;
import com.magicfilter.advanced.MagicAntiqueFilter;
import com.magicfilter.advanced.MagicBeautyFilter;
import com.magicfilter.advanced.MagicBlackCatFilter;
import com.magicfilter.advanced.MagicBrannanFilter;
import com.magicfilter.advanced.MagicBrooklynFilter;
import com.magicfilter.advanced.MagicCalmFilter;
import com.magicfilter.advanced.MagicCoolFilter;
import com.magicfilter.advanced.MagicCrayonFilter;
import com.magicfilter.advanced.MagicEarlyBirdFilter;
import com.magicfilter.advanced.MagicEmeraldFilter;
import com.magicfilter.advanced.MagicEvergreenFilter;
import com.magicfilter.advanced.MagicFreudFilter;
import com.magicfilter.advanced.MagicHealthyFilter;
import com.magicfilter.advanced.MagicHefeFilter;
import com.magicfilter.advanced.MagicHudsonFilter;
import com.magicfilter.advanced.MagicImageAdjustFilter;
import com.magicfilter.advanced.MagicInkwellFilter;
import com.magicfilter.advanced.MagicKevinFilter;
import com.magicfilter.advanced.MagicLatteFilter;
import com.magicfilter.advanced.MagicLomoFilter;
import com.magicfilter.advanced.MagicN1977Filter;
import com.magicfilter.advanced.MagicNashvilleFilter;
import com.magicfilter.advanced.MagicNostalgiaFilter;
import com.magicfilter.advanced.MagicPixarFilter;
import com.magicfilter.advanced.MagicRiseFilter;
import com.magicfilter.advanced.MagicRomanceFilter;
import com.magicfilter.advanced.MagicSakuraFilter;
import com.magicfilter.advanced.MagicSierraFilter;
import com.magicfilter.advanced.MagicSketchFilter;
import com.magicfilter.advanced.MagicSkinWhitenFilter;
import com.magicfilter.advanced.MagicSunriseFilter;
import com.magicfilter.advanced.MagicSunsetFilter;
import com.magicfilter.advanced.MagicSutroFilter;
import com.magicfilter.advanced.MagicSweetsFilter;
import com.magicfilter.advanced.MagicTenderFilter;
import com.magicfilter.advanced.MagicToasterFilter;
import com.magicfilter.advanced.MagicValenciaFilter;
import com.magicfilter.advanced.MagicWaldenFilter;
import com.magicfilter.advanced.MagicWarmFilter;
import com.magicfilter.advanced.MagicWhiteCatFilter;
import com.magicfilter.advanced.MagicXproIIFilter;
import com.magicfilter.base.MagicLookupFilter;
import com.magicfilter.base.gpuimage.GPUImageBrightnessFilter;
import com.magicfilter.base.gpuimage.GPUImageContrastFilter;
import com.magicfilter.base.gpuimage.GPUImageExposureFilter;
import com.magicfilter.base.gpuimage.GPUImageFilter;
import com.magicfilter.base.gpuimage.GPUImageHueFilter;
import com.magicfilter.base.gpuimage.GPUImageSaturationFilter;
import com.magicfilter.base.gpuimage.GPUImageSharpenFilter;

public class MagicFilterFactory {

    public static GPUImageFilter initFilters(MagicFilterType type) {
        switch (type) {
            case NONE:
                return new GPUImageFilter();
            case WHITECAT:
                return new MagicWhiteCatFilter();
            case BLACKCAT:
                return new MagicBlackCatFilter();
            case SKINWHITEN:
                return new MagicSkinWhitenFilter();
            case BEAUTY:
                return new MagicBeautyFilter();
            case ROMANCE:
                return new MagicRomanceFilter();
            case SAKURA:
                return new MagicSakuraFilter();
            case AMARO:
                return new MagicAmaroFilter();
            case WALDEN:
                return new MagicWaldenFilter();
            case ANTIQUE:
                return new MagicAntiqueFilter();
            case CALM:
                return new MagicCalmFilter();
            case BRANNAN:
                return new MagicBrannanFilter();
            case BROOKLYN:
                return new MagicBrooklynFilter();
            case EARLYBIRD:
                return new MagicEarlyBirdFilter();
            case FREUD:
                return new MagicFreudFilter();
            case HEFE:
                return new MagicHefeFilter();
            case HUDSON:
                return new MagicHudsonFilter();
            case INKWELL:
                return new MagicInkwellFilter();
            case KEVIN:
                return new MagicKevinFilter();
            case LOCKUP:
                return new MagicLookupFilter("");
            case LOMO:
                return new MagicLomoFilter();
            case N1977:
                return new MagicN1977Filter();
            case NASHVILLE:
                return new MagicNashvilleFilter();
            case PIXAR:
                return new MagicPixarFilter();
            case RISE:
                return new MagicRiseFilter();
            case SIERRA:
                return new MagicSierraFilter();
            case SUTRO:
                return new MagicSutroFilter();
            case TOASTER2:
                return new MagicToasterFilter();
            case VALENCIA:
                return new MagicValenciaFilter();
            case XPROII:
                return new MagicXproIIFilter();
            case EVERGREEN:
                return new MagicEvergreenFilter();
            case HEALTHY:
                return new MagicHealthyFilter();
            case COOL:
                return new MagicCoolFilter();
            case EMERALD:
                return new MagicEmeraldFilter();
            case LATTE:
                return new MagicLatteFilter();
            case WARM:
                return new MagicWarmFilter();
            case TENDER:
                return new MagicTenderFilter();
            case SWEETS:
                return new MagicSweetsFilter();
            case NOSTALGIA:
                return new MagicNostalgiaFilter();
            case SUNRISE:
                return new MagicSunriseFilter();
            case SUNSET:
                return new MagicSunsetFilter();
            case CRAYON:
                return new MagicCrayonFilter();
            case SKETCH:
                return new MagicSketchFilter();
            //image adjust
            case BRIGHTNESS:
                return new GPUImageBrightnessFilter();
            case CONTRAST:
                return new GPUImageContrastFilter();
            case EXPOSURE:
                return new GPUImageExposureFilter();
            case HUE:
                return new GPUImageHueFilter();
            case SATURATION:
                return new GPUImageSaturationFilter();
            case SHARPEN:
                return new GPUImageSharpenFilter();
            case IMAGE_ADJUST:
                return new MagicImageAdjustFilter();
            default:
                return null;
        }
    }
}
