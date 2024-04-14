import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './tour-step.reducer';

export const TourStepDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const tourStepEntity = useAppSelector(state => state.tourStep.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="tourStepDetailsHeading">
          <Translate contentKey="xploraAdminApp.tourStep.detail.title">TourStep</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{tourStepEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="xploraAdminApp.tourStep.code">Code</Translate>
            </span>
          </dt>
          <dd>{tourStepEntity.code}</dd>
          <dt>
            <span id="stepOrder">
              <Translate contentKey="xploraAdminApp.tourStep.stepOrder">Step Order</Translate>
            </span>
          </dt>
          <dd>{tourStepEntity.stepOrder}</dd>
          <dt>
            <span id="waitTime">
              <Translate contentKey="xploraAdminApp.tourStep.waitTime">Wait Time</Translate>
            </span>
          </dt>
          <dd>{tourStepEntity.waitTime}</dd>
          <dt>
            <span id="driveTime">
              <Translate contentKey="xploraAdminApp.tourStep.driveTime">Drive Time</Translate>
            </span>
          </dt>
          <dd>{tourStepEntity.driveTime}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="xploraAdminApp.tourStep.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {tourStepEntity.createdDate ? (
              <TextFormat value={tourStepEntity.createdDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="xploraAdminApp.tourStep.createdBy">Created By</Translate>
          </dt>
          <dd>{tourStepEntity.createdBy ? tourStepEntity.createdBy.login : ''}</dd>
          <dt>
            <Translate contentKey="xploraAdminApp.tourStep.tour">Tour</Translate>
          </dt>
          <dd>{tourStepEntity.tour ? tourStepEntity.tour.code : ''}</dd>
          <dt>
            <Translate contentKey="xploraAdminApp.tourStep.place">Place</Translate>
          </dt>
          <dd>{tourStepEntity.place ? tourStepEntity.place.code : ''}</dd>
        </dl>
        <Button tag={Link} to="/tour-step" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/tour-step/${tourStepEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TourStepDetail;
