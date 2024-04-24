import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IDestination } from 'app/shared/model/destination.model';
import { getEntities as getDestinations } from 'app/entities/destination/destination.reducer';
import { ITour } from 'app/shared/model/tour.model';
import { getEntities as getTours } from 'app/entities/tour/tour.reducer';
import { ITourCategory } from 'app/shared/model/tour-category.model';
import { getEntities as getTourCategories } from 'app/entities/tour-category/tour-category.reducer';
import { IPlace } from 'app/shared/model/place.model';
import { getEntities as getPlaces } from 'app/entities/place/place.reducer';
import { IPlaceCategory } from 'app/shared/model/place-category.model';
import { getEntities as getPlaceCategories } from 'app/entities/place-category/place-category.reducer';
import { ITourExtraCategory } from 'app/shared/model/tour-extra-category.model';
import { getEntities as getTourExtraCategories } from 'app/entities/tour-extra-category/tour-extra-category.reducer';
import { ITourExtra } from 'app/shared/model/tour-extra.model';
import { getEntities as getTourExtras } from 'app/entities/tour-extra/tour-extra.reducer';
import { IVehicle } from 'app/shared/model/vehicle.model';
import { getEntities as getVehicles } from 'app/entities/vehicle/vehicle.reducer';
import { IDriver } from 'app/shared/model/driver.model';
import { getEntities as getDrivers } from 'app/entities/driver/driver.reducer';
import { IImageFile } from 'app/shared/model/image-file.model';
import { getEntity, updateEntity, createEntity, reset } from './image-file.reducer';

export const ImageFileUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const users = useAppSelector(state => state.userManagement.users);
  const destinations = useAppSelector(state => state.destination.entities);
  const tours = useAppSelector(state => state.tour.entities);
  const tourCategories = useAppSelector(state => state.tourCategory.entities);
  const places = useAppSelector(state => state.place.entities);
  const placeCategories = useAppSelector(state => state.placeCategory.entities);
  const tourExtraCategories = useAppSelector(state => state.tourExtraCategory.entities);
  const tourExtras = useAppSelector(state => state.tourExtra.entities);
  const vehicles = useAppSelector(state => state.vehicle.entities);
  const drivers = useAppSelector(state => state.driver.entities);
  const imageFileEntity = useAppSelector(state => state.imageFile.entity);
  const loading = useAppSelector(state => state.imageFile.loading);
  const updating = useAppSelector(state => state.imageFile.updating);
  const updateSuccess = useAppSelector(state => state.imageFile.updateSuccess);

  const handleClose = () => {
    navigate('/image-file' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getUsers({}));
    dispatch(getDestinations({}));
    dispatch(getTours({}));
    dispatch(getTourCategories({}));
    dispatch(getPlaces({}));
    dispatch(getPlaceCategories({}));
    dispatch(getTourExtraCategories({}));
    dispatch(getTourExtras({}));
    dispatch(getVehicles({}));
    dispatch(getDrivers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }

    const entity = {
      ...imageFileEntity,
      ...values,
      createdBy: users.find(it => it.id.toString() === values.createdBy?.toString()),
      destination: destinations.find(it => it.id.toString() === values.destination?.toString()),
      tour: tours.find(it => it.id.toString() === values.tour?.toString()),
      tourCategory: tourCategories.find(it => it.id.toString() === values.tourCategory?.toString()),
      place: places.find(it => it.id.toString() === values.place?.toString()),
      placeCategory: placeCategories.find(it => it.id.toString() === values.placeCategory?.toString()),
      tourExtraCategory: tourExtraCategories.find(it => it.id.toString() === values.tourExtraCategory?.toString()),
      tourExtra: tourExtras.find(it => it.id.toString() === values.tourExtra?.toString()),
      vehicle: vehicles.find(it => it.id.toString() === values.vehicle?.toString()),
      driver: drivers.find(it => it.id.toString() === values.driver?.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...imageFileEntity,
          createdBy: imageFileEntity?.createdBy?.id,
          destination: imageFileEntity?.destination?.id,
          tour: imageFileEntity?.tour?.id,
          tourCategory: imageFileEntity?.tourCategory?.id,
          place: imageFileEntity?.place?.id,
          placeCategory: imageFileEntity?.placeCategory?.id,
          tourExtraCategory: imageFileEntity?.tourExtraCategory?.id,
          tourExtra: imageFileEntity?.tourExtra?.id,
          vehicle: imageFileEntity?.vehicle?.id,
          driver: imageFileEntity?.driver?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="xploraAdminApp.imageFile.home.createOrEditLabel" data-cy="ImageFileCreateUpdateHeading">
            <Translate contentKey="xploraAdminApp.imageFile.home.createOrEditLabel">Create or edit a ImageFile</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="image-file-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('xploraAdminApp.imageFile.code')}
                id="image-file-code"
                name="code"
                data-cy="code"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('xploraAdminApp.imageFile.title')}
                id="image-file-title"
                name="title"
                data-cy="title"
                type="text"
              />
              <ValidatedField label={translate('xploraAdminApp.imageFile.alt')} id="image-file-alt" name="alt" data-cy="alt" type="text" />
              <ValidatedField
                label={translate('xploraAdminApp.imageFile.filename')}
                id="image-file-filename"
                name="filename"
                data-cy="filename"
                type="text"
              />
              <ValidatedBlobField
                label={translate('xploraAdminApp.imageFile.data')}
                id="image-file-data"
                name="data"
                data-cy="data"
                isImage
                accept="image/*"
              />
              <ValidatedField
                label={translate('xploraAdminApp.imageFile.createdDate')}
                id="image-file-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="date"
              />
              <ValidatedField
                id="image-file-createdBy"
                name="createdBy"
                data-cy="createdBy"
                label={translate('xploraAdminApp.imageFile.createdBy')}
                type="select"
              >
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.login}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="image-file-destination"
                name="destination"
                data-cy="destination"
                label={translate('xploraAdminApp.imageFile.destination')}
                type="select"
              >
                <option value="" key="0" />
                {destinations
                  ? destinations.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.code}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="image-file-tour"
                name="tour"
                data-cy="tour"
                label={translate('xploraAdminApp.imageFile.tour')}
                type="select"
              >
                <option value="" key="0" />
                {tours
                  ? tours.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.code}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="image-file-tourCategory"
                name="tourCategory"
                data-cy="tourCategory"
                label={translate('xploraAdminApp.imageFile.tourCategory')}
                type="select"
              >
                <option value="" key="0" />
                {tourCategories
                  ? tourCategories.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.code}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="image-file-place"
                name="place"
                data-cy="place"
                label={translate('xploraAdminApp.imageFile.place')}
                type="select"
              >
                <option value="" key="0" />
                {places
                  ? places.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.code}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="image-file-placeCategory"
                name="placeCategory"
                data-cy="placeCategory"
                label={translate('xploraAdminApp.imageFile.placeCategory')}
                type="select"
              >
                <option value="" key="0" />
                {placeCategories
                  ? placeCategories.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.code}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="image-file-tourExtraCategory"
                name="tourExtraCategory"
                data-cy="tourExtraCategory"
                label={translate('xploraAdminApp.imageFile.tourExtraCategory')}
                type="select"
              >
                <option value="" key="0" />
                {tourExtraCategories
                  ? tourExtraCategories.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.code}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="image-file-tourExtra"
                name="tourExtra"
                data-cy="tourExtra"
                label={translate('xploraAdminApp.imageFile.tourExtra')}
                type="select"
              >
                <option value="" key="0" />
                {tourExtras
                  ? tourExtras.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.code}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="image-file-vehicle"
                name="vehicle"
                data-cy="vehicle"
                label={translate('xploraAdminApp.imageFile.vehicle')}
                type="select"
              >
                <option value="" key="0" />
                {vehicles
                  ? vehicles.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.plate}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="image-file-driver"
                name="driver"
                data-cy="driver"
                label={translate('xploraAdminApp.imageFile.driver')}
                type="select"
              >
                <option value="" key="0" />
                {drivers
                  ? drivers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/image-file" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ImageFileUpdate;
