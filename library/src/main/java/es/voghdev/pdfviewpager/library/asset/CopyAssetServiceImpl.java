/*
 * Copyright (C) 2016 Olmo Gallegos Hernández.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package es.voghdev.pdfviewpager.library.asset;

import android.content.Context;

import es.voghdev.pdfviewpager.library.service.CopyAssetService;

public class CopyAssetServiceImpl implements CopyAsset {
    private Context context;

    public CopyAssetServiceImpl(Context context) {
        this.context = context;
    }

    @Override
    public void copy(String assetName, String destinationPath) {
        CopyAssetService.startCopyAction(context, assetName, destinationPath);
    }
}
